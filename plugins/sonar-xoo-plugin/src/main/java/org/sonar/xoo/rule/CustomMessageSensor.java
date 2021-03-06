/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.xoo.rule;

import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issuable;
import org.sonar.api.rule.RuleKey;

public class CustomMessageSensor extends AbstractDeprecatedXooRuleSensor {

  public static final String RULE_KEY = "CustomMessage";

  private static final String MESSAGE_PROPERTY = "sonar.customMessage.message";

  private final ResourcePerspectives perspectives;
  private final Settings settings;

  public CustomMessageSensor(ResourcePerspectives perspectives, Settings settings, FileSystem fs, ActiveRules activeRules) {
    super(fs, activeRules);
    this.perspectives = perspectives;
    this.settings = settings;
  }

  @Override
  protected String getRuleKey() {
    return RULE_KEY;
  }

  @Override
  protected void processFile(InputFile inputFile, org.sonar.api.resources.File sonarFile, SensorContext context, RuleKey ruleKey, String languageKey) {
    Issuable issuable = perspectives.as(Issuable.class, sonarFile);
    if (issuable != null) {
      issuable.addIssue(issuable.newIssueBuilder()
        .ruleKey(ruleKey)
        .message(settings.getString(MESSAGE_PROPERTY))
        .build());
    }
  }
}
