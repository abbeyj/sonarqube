/*
 * Copyright (C) 2009-2014 SonarSource SA
 * All rights reserved
 * mailto:contact AT sonarsource DOT com
 */
package issue.suite;

import com.sonar.orchestrator.Orchestrator;
import java.util.List;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sonar.wsclient.issue.Issue;
import org.sonar.wsclient.issue.IssueClient;
import org.sonar.wsclient.issue.IssueQuery;
import org.sonar.wsclient.issue.Issues;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ItUtils.pluginArtifact;
import static util.ItUtils.xooPlugin;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  CommonRulesTest.class, IssueWorkflowTest.class, ManualRulesTest.class, CustomRulesTest.class, IssueActionTest.class
})
public class IssueTestSuite {

  @ClassRule
  public static final Orchestrator ORCHESTRATOR = Orchestrator.builderEnv()
    .addPlugin(xooPlugin())
    .addPlugin(pluginArtifact("issue-action-plugin"))
    .build();

  static IssueClient adminIssueClient() {
    return ORCHESTRATOR.getServer().adminWsClient().issueClient();
  }

  static IssueClient issueClient() {
    return ORCHESTRATOR.getServer().wsClient().issueClient();
  }

  static Issue searchRandomIssue() {
    List<Issue> issues = searchIssues(IssueQuery.create());
    assertThat(issues).isNotEmpty();
    return issues.get(0);
  }

  static Issues search(IssueQuery issueQuery) {
    issueQuery.urlParams().put("additionalFields", "_all");
    return issueClient().find(issueQuery);
  }

  static List<Issue> searchIssues(IssueQuery issueQuery) {
    return search(issueQuery).list();
  }

  static Issue searchIssueByKey(String issueKey) {
    List<Issue> issues = searchIssues(IssueQuery.create().issues(issueKey));
    assertThat(issues).hasSize(1);
    return issues.get(0);
  }

}
