package agh.cs.lab1;
import java.util.ArrayList;
import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        out.println("Start");
        if(args.length > 0) {
            run(convertFromStringToDirections(args));
        }
        out.println("Stop");
    }

    public static Directions[] convertFromStringToDirections(String[] args){
        ArrayList<Directions> dirsTemp = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "f":
                    dirsTemp.add(Directions.FORWARD);
                    break;
                case "b":
                    dirsTemp.add(Directions.BACKWARD);
                    break;
                case "r":
                    dirsTemp.add(Directions.RIGHT);
                    break;
                case "l":
                    dirsTemp.add(Directions.LEFT);
                    break;
            }
        }
        Directions[] dirs = new Directions[dirsTemp.size()];
        dirsTemp.toArray(dirs);
        return dirs;
    }

    public static void run(Directions[] args){
            for (Directions arg : args) {
                switch(arg){
                    case FORWARD:
                        out.println("Zwierzak idzie do przodu.");
                        break;
                    case BACKWARD:
                        out.println("Zwierzak idzie do tylu.");
                        break;
                    case RIGHT:
                        out.println("Zwierzak skreca w prawo.");
                        break;
                    case LEFT:
                        out.println("Zwierzak skreca w lewo.");
                        break;
                }
            }
    }
}
