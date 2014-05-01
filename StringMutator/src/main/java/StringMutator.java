import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by shaun.netherby on 3/27/2014.
 */
public class StringMutator {
    public static void main(String[] args) {
        String original = "Zach";
        System.out.println("----------Before----------\n" + original + "\n");
        mutate("Zach");
        System.out.println("\n----------After-----------");
        System.out.println("Zach");
    }

    public static void mutate(String original) {
        try {
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);
            StringMutation mutation = StringMutation.values()[new Random().nextInt(StringMutation.values().length)];
            char[] charArr;
            List<Character> chars = new ArrayList<Character>();
            for (char c : original.toCharArray())
                chars.add(c);
            switch (mutation.getMutationType()) {
                case ANAGRAM:
                    Collections.shuffle(chars);
                    charArr = new char[chars.size()];
                    for (int i = 0; i < chars.size(); i++) {
                        charArr[i] = chars.get(i);
                    }
                    break;
                case PREFIX:
                    String mutated = mutation.getValue() + original;
                    charArr = mutated.toCharArray();
                    break;
                case REPLACE:
                    charArr = mutation.getValue().toCharArray();
                    break;
                case REVERSE:
                    Collections.reverse(chars);
                    charArr = new char[chars.size()];
                    for (int i = 0; i < chars.size(); i++) {
                        charArr[i] = chars.get(i);
                    }
                    break;
                case SUFFIX:
                default:
                    charArr = new String(original + mutation.getValue()).toCharArray();
                    break;
            }
            System.out.println("Mutating " + original + " by " + mutation.getMutationType());

            valueField.set(original, charArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

enum StringMutation {
    FLYING("Flying", MutationType.PREFIX),
    DERP("durp...", MutationType.SUFFIX),
    AHHH("AHHHH"),
    REVERSE(MutationType.REVERSE),
    ANAGRAM(MutationType.ANAGRAM);

    enum MutationType {REPLACE, PREFIX, SUFFIX, ANAGRAM, REVERSE;}

    private String value;
    private MutationType mutationType;

    private StringMutation(String value) {
        this.value = value;
        this.mutationType = MutationType.REPLACE;
    }

    private StringMutation(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    private StringMutation(String value, MutationType mutationType) {
        this.value = value;
        this.mutationType = mutationType;
    }

    String getValue() {
        return value;
    }

    MutationType getMutationType() {
        return mutationType;
    }


}
