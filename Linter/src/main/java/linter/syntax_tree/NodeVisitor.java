package linter.syntax_tree;

public interface NodeVisitor {
    boolean visit(ProductionNode node);
    void visit(TokenNode node);
}