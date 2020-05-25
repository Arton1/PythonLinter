package linter.syntax_tree;


public interface TreeElement {
    public void accept(TreeElementVisitor visitor);
}
