package banco;

import banco.db.ClienteDB;
import banco.db.ContaDB;
import banco.modelo.Cliente;
import banco.modelo.Conta;

public class Principal {

    public static void main(String[] args) {
        ClienteDB clienteDB = new ClienteDB();
        ContaDB contaDB = new ContaDB();
        
        clienteDB.deleteAllEntries();
        contaDB.deleteAllEntries();

        Cliente teste = new Cliente(0, "Alberto", "Rua das flores", 1112223, 12312311, 992222, 1000);
        clienteDB.addCliente(teste);

        Conta conta1 = new Conta(0, 2323, 1, 5000, teste);
        contaDB.addConta(conta1);

        for (Conta conta : contaDB.getContas()) {
            System.out.println("Id:       " + conta.getId());
            System.out.println("Agencia:  " + conta.getAgencia());
            System.out.println("Numero:   " + conta.getNumero());
            System.out.println("Saldo:    " + conta.getSaldo());
            System.out.println("Cliente: {");
            System.out.println("            Nome:         " + conta.getCliente().getNome());
            System.out.println("            Endereco:     " + conta.getCliente().getEndereco());
            System.out.println("            CPF:          " + conta.getCliente().getCpf());
            System.out.println("            RG:           " + conta.getCliente().getRg());
            System.out.println("            Telefone:     " + conta.getCliente().getTelefone());
            System.out.println("            Renda Mensal: " + conta.getCliente().getRendaMensal());
            System.out.println("         }");
        }
    }
}
