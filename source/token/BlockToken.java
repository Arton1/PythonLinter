package token;

public class BlockToken implements Token{
    public enum BlockType {
        NEWLINE, INDENT, DEINDENT, TWO_DOTTED
    }

    BlockType type;

    BlockToken(BlockType type){
        this.type = type;
    }
}