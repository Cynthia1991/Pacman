package pacman372.dementiaaid.EntityClasses;

/**
 * Created by fuqian on 21/09/2015.
 */
public class Entity<T> {
    public Entity(int id, T data){
        this.id = id;
        this.data = data;
    }
    public T data;
    public int id;
}
