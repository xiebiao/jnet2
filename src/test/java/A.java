import java.util.HashMap;
import java.util.Map;



public class A {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<Long ,String> map = new HashMap<Long ,String>();
        Long agentId = new Long(1001L);
        Long agentTemp = new Long(1001);
        map.put(agentId,"putengfei");
        System.out.println(map.get(agentTemp)); 


    }

}
