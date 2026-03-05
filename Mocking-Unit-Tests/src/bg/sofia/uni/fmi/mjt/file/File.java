package bg.sofia.uni.fmi.mjt.file;

import java.util.Objects;

public class File {

    private String content;

    public File(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;

    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.content.equals(((File) o).content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
