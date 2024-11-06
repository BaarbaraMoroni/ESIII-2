package eng3.barbara;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import org.junit.Test;

public class TesteCalculaDataDevolucao {

    // teste com 3 livros
    @Test
    public void testEmprestimoComTresLivros() {
        Emprestimo emprestimo = new Emprestimo();

        Livro livro1 = new Livro(1); // Prazo  dias
        Livro livro2 = new Livro(2); 
        Livro livro3 = new Livro(3); 

        emprestimo.itens.add(new Item(livro1));
        emprestimo.itens.add(new Item(livro2));
        emprestimo.itens.add(new Item(livro3));

        Date dataEmprestimo = new Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dataEmprestimo);

       
        calendario.add(Calendar.DATE, 6);
        Date dataEsperada = calendario.getTime();

        assertEquals(dataEsperada.toString(), emprestimo.calculaDataDevolucao().toString());
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void testExcecaoParaMaisDeCincoLivros() {
        Emprestimo emprestimo = new Emprestimo();

        // Add 6 livros
        for (int i = 0; i < 6; i++) {
            emprestimo.itens.add(new Item(new Livro(i + 1)));
        }

        emprestimo.calculaDataDevolucao(); 
    }

    // teste 1 livro
    @Test
    public void testEmprestimoComUmLivro() {
        Emprestimo emprestimo = new Emprestimo();

        Livro livro1 = new Livro(1); // Prazo 2 dias

        emprestimo.itens.add(new Item(livro1));

        Date dataEmprestimo = new Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dataEmprestimo);
        calendario.add(Calendar.DATE, 2); // 2 dias

        Date dataEsperada = calendario.getTime();
        assertEquals(dataEsperada.toString(), emprestimo.calculaDataDevolucao().toString());
    }

    // Teste data nula
    @Test
    public void testItemComDataNula() {
        Item item = new Item(new Livro(1));
        item.calculaDataDevolucao(null);
        assertNull(item.getDataDevolucao());
    }

    // tteste setDataDevolucao
    // cenário 1- data válida
    // cenário 2- data nula
    // ccenário 3- data futura (verificar se uma data futura é válida)
    // cenário 4- data passada ( caso o sistema tenha restrição de datas)
@Test
public void testSetDataDevolucao() {
    Item item = new Item(new Livro(1));
    

    Date data = new Date();
    item.setDataDevolucao(data);
    assertEquals("A data de devolução deve ser a mesma que foi definida", data, item.getDataDevolucao());

    item.setDataDevolucao(null);
    assertNull("A data de devolução deve ser nula quando definida como null", item.getDataDevolucao());

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 10); // 10 dias no futuro
    Date futureDate = calendar.getTime();
    item.setDataDevolucao(futureDate);
    assertEquals("A data de devolução deve ser a data futura definida", futureDate, item.getDataDevolucao());

    calendar.add(Calendar.DATE, -20); // 20 dias no passado
    Date pastDate = calendar.getTime();
    item.setDataDevolucao(pastDate);
    assertEquals("A data de devolução deve aceitar uma data passada", pastDate, item.getDataDevolucao());
}
