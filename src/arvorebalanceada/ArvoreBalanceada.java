/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorebalanceada;

/**
 *
 * @author Wali
 */
public class ArvoreBalanceada {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arvore a = new Arvore();
        
        a.inserir(7, a.getRaiz());
        a.inserir(8, a.getRaiz());
        a.inserir(3, a.getRaiz());
        
        
        
      
        System.out.println("Antes de remover: \n");
        a.imprime(a.getRaiz());
        a.remover(7, a.getRaiz());
        System.out.println("Depois de remover: \n");
        a.imprime(a.getRaiz());
        
        
    }
    
}
