import commands.MulTableCommand;
import org.junit.Assert;
import org.junit.Test;

public class MulTableTest extends Assert {

    @Test
    public void testGenTable() {
        MulTableCommand cmd = new MulTableCommand();
        assertEquals(String.format("Таблица умножения: 2 x 2%n" +
                "<pre> 1 2%n" +
                " 2 4%n" +
                "</pre>"), cmd.genMulTable(2));
    }
}
