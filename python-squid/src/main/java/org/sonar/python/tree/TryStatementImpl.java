/*
 * SonarQube Python Plugin
 * Copyright (C) 2011-2019 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.python.tree;

import com.sonar.sslr.api.AstNode;
import java.util.Objects;
import org.sonar.python.api.tree.Token;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import org.sonar.python.api.tree.ElseStatement;
import org.sonar.python.api.tree.ExceptClause;
import org.sonar.python.api.tree.FinallyClause;
import org.sonar.python.api.tree.StatementList;
import org.sonar.python.api.tree.TreeVisitor;
import org.sonar.python.api.tree.TryStatement;
import org.sonar.python.api.tree.Tree;

public class TryStatementImpl extends PyTree implements TryStatement {
  private final Token tryKeyword;
  private final Token colon;
  private final Token newLine;
  private final Token indent;
  private final StatementList tryBody;
  private final Token dedent;
  private final List<ExceptClause> exceptClauses;
  private final FinallyClause finallyClause;
  private final ElseStatement elseStatement;

  public TryStatementImpl(AstNode astNode, Token tryKeyword, Token colon, @Nullable Token newLine, @Nullable Token indent, StatementList tryBody,
                          @Nullable Token dedent, List<ExceptClause> exceptClauses, @Nullable FinallyClause finallyClause, @Nullable ElseStatement elseStatement) {
    super(astNode);
    this.tryKeyword = tryKeyword;
    this.colon = colon;
    this.newLine = newLine;
    this.indent = indent;
    this.tryBody = tryBody;
    this.dedent = dedent;
    this.exceptClauses = exceptClauses;
    this.finallyClause = finallyClause;
    this.elseStatement = elseStatement;
  }

  @Override
  public Token tryKeyword() {
    return tryKeyword;
  }

  @Override
  public List<ExceptClause> exceptClauses() {
    return exceptClauses;
  }

  @CheckForNull
  @Override
  public FinallyClause finallyClause() {
    return finallyClause;
  }

  @CheckForNull
  @Override
  public ElseStatement elseClause() {
    return elseStatement;
  }

  @Override
  public StatementList body() {
    return tryBody;
  }

  @Override
  public Kind getKind() {
    return Kind.TRY_STMT;
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitTryStatement(this);
  }

  @Override
  public List<Tree> children() {
    return Stream.of(Arrays.asList(tryKeyword, colon, newLine, indent, tryBody, dedent), exceptClauses, Arrays.asList(finallyClause, elseStatement))
      .flatMap(List::stream).filter(Objects::nonNull).collect(Collectors.toList());
  }
}