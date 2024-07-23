package org.example.security;

import java.util.Objects;
import java.util.stream.Stream;

public class Validation {

    public boolean checkNull(String data){
        return data != null && !data.equals("");
    }

    public String conversionPNField(String PNField){
        return Stream.of(PNField).filter(Objects::nonNull)
                .map(s -> s.replace(" ", "").replace("-", ""))
                .filter(s -> {
                    boolean h = true;
                    for(char z: s.toCharArray()){
                        if(Character.isLetter(z)){
                            h = false;
                        }
                    }
                    return h;
                }).toString();
    }

    public String conversionPWField(String PWField){
        return Stream.of(PWField).filter(Objects::nonNull)
                .map(s -> s.replace(" ", ""))
                .toString();

    }

    public String[] conversionFIOField(String FIOField){
        return Stream.of(FIOField).filter(Objects::nonNull)
                .filter(s -> {
                    boolean h = true;
                    for (char z : s.toCharArray()) {
                        if (!Character.isLetter(z)) {
                            h = false;
                        }
                    }
                    return h;
                }).toString().split(" ");
    }
}
