package example.framework.template;

public interface Renderer {

    public Class getTypeToRender();

    public String toString(Object obj);

    public String toString(Object obj, String formatName);
}
