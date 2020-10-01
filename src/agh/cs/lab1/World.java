package agh.cs.lab1;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        out.println("Start");
        if(args.length > 0) {
            if(!Arrays.stream(args).allMatch( a -> a.equals("f") || a.equals("b") || a.equals("r") || a.equals("l") )){
                throw new IllegalArgumentException("All arguments must come from the set {f, b, r, l}");
            }
            List<Directions> list = Arrays.stream(args)
                    .map(a -> {
                        if(a.equals("f")) return Directions.FORWARD;
                        if(a.equals("b")) return Directions.BACKWARD;
                        if(a.equals("r")) return Directions.RIGHT;
                        else return Directions.LEFT;
                    })
                    .collect(Collectors.toList());
            run(list);
        }
        out.println("Stop");
    }

    public static void run(List<Directions> args){
            args.forEach(d -> {
                if(d == Directions.FORWARD) out.println("Zwierzak idzie do przodu");
                if(d == Directions.BACKWARD) out.println("Zwierzak idzie do tylu");
                if(d == Directions.RIGHT) out.println("Zwierzak skreca w prawo");
                if(d == Directions.LEFT) out.println("Zwierzak skreca w lewo");
            });
    }
}
