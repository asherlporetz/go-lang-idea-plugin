/*
 * Copyright 2013-2015 Sergey Ignatov, Alexander Zolotov, Florin Patan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goide.usages;

import com.goide.GoCodeInsightFixtureTestCase;
import com.goide.psi.GoFunctionDeclaration;
import com.goide.psi.GoTypeSpec;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageInfo;
import com.intellij.usages.*;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class GoFileStructureGroupRuleTest extends GoCodeInsightFixtureTestCase {
  public void testMethod() {
    assertInstanceOf(getGroupElement(), GoFunctionDeclaration.class);
  }

  public void testType() {
    assertInstanceOf(getGroupElement(), GoTypeSpec.class);
  }

  @NotNull
  private PsiElement getGroupElement() {
    List<UsageGroup> groups = new GoFileStructureGroupRuleProvider().getUsageGroupingRule(null).getParentGroupsFor(findSingleUsage(), new UsageTarget[]{});
    assertNotNull(groups);
    assertSize(1, groups);
    assertInstanceOf(groups.get(0), PsiElementUsageGroupBase.class);
    return ((PsiElementUsageGroupBase)groups.get(0)).getElement();
  }

  @NotNull
  private Usage findSingleUsage() {
    Collection<UsageInfo> infos = myFixture.testFindUsages(getTestName(true) + ".go");
    assertEquals(1, infos.size());
    UsageInfo item = ContainerUtil.getFirstItem(infos);
    assertNotNull(item);
    return new UsageInfo2UsageAdapter(item);
  }

  @Override
  protected String getBasePath() {
    return "usages";
  }
}
