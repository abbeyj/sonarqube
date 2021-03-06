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
package org.sonar.server.computation.analysis;

import java.util.Date;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnalysisMetadataHolderImplTest {
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Date someDate = new Date();

  @Test
  public void getAnalysisDate_returns_date_with_same_time_as_the_one_set_with_setAnalysisDate() throws InterruptedException {
    AnalysisMetadataHolderImpl underTest = new AnalysisMetadataHolderImpl();

    underTest.setAnalysisDate(someDate);

    Thread.sleep(10);

    Date analysisDate = underTest.getAnalysisDate();
    assertThat(analysisDate.getTime()).isEqualTo(someDate.getTime());
    assertThat(analysisDate).isNotSameAs(someDate);
  }

  @Test
  public void getAnalysisDate_throws_ISE_when_holder_is_not_initialized() {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("Analysis date has not been set");

    new AnalysisMetadataHolderImpl().getAnalysisDate();
  }

  @Test
  public void setAnalysisDate_throws_ISE_when_called_twice() {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("Analysis date has already been set");

    AnalysisMetadataHolderImpl underTest = new AnalysisMetadataHolderImpl();

    underTest.setAnalysisDate(someDate);
    underTest.setAnalysisDate(someDate);
  }

  @Test
  public void isFirstAnalysis_return_true() throws Exception {
    AnalysisMetadataHolderImpl underTest = new AnalysisMetadataHolderImpl();

    underTest.setIsFirstAnalysis(true);
    assertThat(underTest.isFirstAnalysis()).isTrue();
  }

  @Test
  public void isFirstAnalysis_return_false() throws Exception {
    AnalysisMetadataHolderImpl underTest = new AnalysisMetadataHolderImpl();

    underTest.setIsFirstAnalysis(false);
    assertThat(underTest.isFirstAnalysis()).isFalse();
  }

  @Test
  public void isFirstAnalysis_throws_ISE_when_holder_is_not_initialized() {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("firstAnalysis flag has not been set");

    new AnalysisMetadataHolderImpl().isFirstAnalysis();
  }

  @Test
  public void setIsFirstAnalysis_throws_ISE_when_called_twice() {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("firstAnalysis flag has already been set");

    AnalysisMetadataHolderImpl underTest = new AnalysisMetadataHolderImpl();

    underTest.setIsFirstAnalysis(true);
    underTest.setIsFirstAnalysis(true);
  }
}
