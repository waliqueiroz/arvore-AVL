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
public class Arvore {

    private No raiz;

    public Arvore() {
        raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public No getRaiz() {
        return raiz;
    }
    //Insere e já balanceia usando o método de balancear
    public void inserir(int i, No atual) {
        if (isEmpty()) {//se vazio preenche a raiz
            No novo = new No(i);
            raiz = novo;
            defineFB(raiz);//define o fator de balanceamento de cada nó
        } else {
            if (i > atual.getInfo()) {
                if (atual.getDir() == null) {//se o no a ser inserido for maior que o atual e o da direita do atual for vazio, insere la
                    No novo = new No(i);
                    atual.setDir(novo);
                    novo.setPai(atual);
                    defineFB(raiz);//define o fator de balanceamento de cada nó
                    raiz = balanceia(raiz);//Balanceia a arvore
                } else { //senão, faz a recursão, passando o nó da direita do atual.
                    inserir(i, atual.getDir());
                }
            } else if (i < atual.getInfo()) { //mesma coisa aqui, só que com o nó da esquerda, se for menor
                if (atual.getEsq() == null) {
                    No novo = new No(i);
                    atual.setEsq(novo);
                    novo.setPai(atual);
                    defineFB(raiz);//define o fator de balanceamento de cada nó
                    raiz = balanceia(raiz);//Balanceia a arvore
                } else {
                    inserir(i, atual.getEsq());
                }
            } else {//se o nó já existir
                System.out.println("Impossível Inserir!");
            }
        }
    }
    //imprime todos os nós e mostra o fator de balanceamento de cada um
    public void imprime(No f) {
        if(isEmpty()){
            System.out.println("Arvore vazia!");
        }else{
            System.out.println("Nó: " + f.getInfo() + " - Seu fator de balanceamento é: " + f.getBalanceamento());
            if (f.getDir() != null) {
                imprime(f.getDir());
            }
            if (f.getEsq() != null) {
                imprime(f.getEsq());
            }
        }
    }
    //remoeve e já balanceia usando o método de balancear
    public No remover(int i, No atual) {
        if (isEmpty()) {//Se a arvore tiver vazia, nao faz nada
            System.out.println("Arvore vazia!");
        } else if (i > atual.getInfo()) {//Se o valor a ser removido for maior q o do no atual, anda pra direita e atribui o retorno do metodo ao no direito passando ele mesmo como parametro
            atual.setDir(remover(i, atual.getDir()));
        } else if (i < atual.getInfo()) {//se for menor, faz a mesma coisa do passo anterior, só q indo pra esquerda
            atual.setEsq(remover(i, atual.getEsq()));
        } else {//Se for igual, faz as verificações de remoção
            if (atual.getDir() == null && atual.getEsq() == null) {//se o nó não tiver subarvores, ou seja, for uma folha, basta anular ele
                if(atual==raiz){
                    raiz=null;
                }else{
                     atual = null;
                }
               
            } else if (atual.getEsq() == null) {//se o nó atual só tiver a subarvore da direita, pega o valor da raiz dessa subarvore e substitui pelo atual
                atual = atual.getDir();
            } else if (atual.getDir() == null) {//se o atual tiver só a subarvore da esquerda,faz a mesma coisa , só que troca o nó pela raiz da direita
                atual = atual.getEsq();
            } else {//Se o nó tiver as duas subárvores, o bixo pega um pouco
                /*Cria um nó auxiliar pra percorrer a arvore até o valor mais a direita da subarvore da esquerda do nó atual
                 e seta ele como o valor da esquerda do atual*/
                No aux = atual.getEsq();
                while (aux.getDir() != null) {//Faz o aux ir até o valor mais a direita
                    aux = aux.getDir();
                }
                atual.setInfo(aux.getInfo()); //substitui o valor do nó atual pelo valor do auxiliar
                aux.setInfo(i); // substitui o valor do auxiliar pelo atual
                atual.setEsq(remover(i, atual.getEsq()));//chama a recursão pra remover o valor do atual, q agora tá no auxiliar, por isso, vai remover o auxiliar                
            }
        }
        if(raiz!=null){
            defineFB(raiz);//atualiza o fator de balanceamento de cada nó
            raiz = balanceia(raiz);//Balanceia a arvore
        }
        return atual;// retorna o atual
    }

    public int altura(No atual) {//Verifica a altura de um determinado nó
        if (atual == null) {//Se o nó for nulo, sua altura será -1
            return -1;
        }
        if (atual.getDir() == null && atual.getEsq() == null) {//Se ele não tiver nenhum filho, sua altura será 0
            return 0;
        } else if (atual.getEsq() == null) {//Se o nó tiver apenas um filho, sua altura será 1 + a altura do nó filho
            return 1 + altura(atual.getDir());
        } else if (atual.getDir() == null) { //Mesma do passo anterior aqui
            return 1 + altura(atual.getEsq());
        } else { //Se ele tiver dois filhos, temos q verificar qual filho é mais "alto"
            if (altura(atual.getEsq()) > altura(atual.getDir())) {//a altura do nó será a soma de 1+ a altura do filho mais alto
                return 1 + altura(atual.getEsq());
            } else {
                return 1 + altura(atual.getDir());
            }
        }//e assim recursivamente, até chegar nas folhas q não terão filhos, a altura será 0 e assim a recursão para.
    }

    public void defineFB(No atual) {//Define o valor de balanceamento de cada nó com base na altura (adicionei um atributo pra armazenar o balanceamento na classe nó)
        atual.setBalanceamento(altura(atual.getEsq()) - altura(atual.getDir()));//O valor do balanceamendo será a altura do filho da direita menos a altura do da direita
        if (atual.getDir() != null) {//verifica todos os nós
            defineFB(atual.getDir());
        }
        if (atual.getEsq() != null) {//verifica todos os nós
            defineFB(atual.getEsq());
        }
    }

    public No rotacaoADireita(No atual) {
        No aux = atual.getEsq(); //Armazena o valor do nó da esquerda do atual
        aux.setPai(atual.getPai());
        if (aux.getDir() != null) {//tratamento para quando a árvore é degenerada
            aux.getDir().setPai(atual);
        }
        atual.setPai(aux);
        atual.setEsq(aux.getDir());//Joga o valor da direita do nó da esquerda do atual, na esquerda do atual        
        aux.setDir(atual);//troca o valor da direita do nó da esquerda pelo atual 
        if (aux.getPai() != null) {//Se aux não for a raiz principal, configura o pai pra apontar pro novo nó
            if (aux.getPai().getDir() == atual) {
                aux.getPai().setDir(aux);
            } else if (aux.getPai().getEsq() == atual) {
                aux.getPai().setEsq(aux);
            }
        }
        defineFB(aux);//atualiza o valor do balanceamento
        return aux; //retorna o valor do nó da esquerda q é o novo pai
    }

    //mesma coisa do rotação a direita, só que invertido pra esquerda
    public No rotacaoAEsquerda(No atual) {
        No aux = atual.getDir();
        aux.setPai(atual.getPai());
        if (aux.getEsq() != null) {//tratamento para quando a árvore é degenerada
            aux.getEsq().setPai(atual);
        }

        atual.setPai(aux);
        atual.setDir(aux.getEsq());
        aux.setEsq(atual);
        if (aux.getPai() != null) {
            if (aux.getPai().getDir() == atual) {
                aux.getPai().setDir(aux);
            } else if (aux.getPai().getEsq() == atual) {
                aux.getPai().setEsq(aux);
            }
        }
        defineFB(aux);//atualiza o valor do balanceamento
        return aux;
    }

    public No rotacaoDuplaDireita(No atual) {
        No aux = atual.getEsq();//Armazena o valor do filho da esquerda
        atual.setEsq(rotacaoAEsquerda(aux));//faz uma rotação para a esquerda no filho da esquerda
        No aux2 = rotacaoADireita(atual); //Faz uma rotação para a direita no atual/pai com o filho da esquerda já rodado
        return aux2; //retorna o nó q será o novo pai com seus filhos
    }

    //mesma coisa do de rotação dupla pra direita, só que invertido pra esquerda
    public No rotacaoDuplaEsquerda(No atual) {
        No aux = atual.getDir();
        atual.setDir(rotacaoADireita(aux));
        No aux2 = rotacaoAEsquerda(atual);
        return aux2;
    }

    public No balanceia(No atual) {//Recebe como parâmetro a raiz
        /*Se o nó atual tiver FB=2 e o seu filho da esquerda dele tiver Fb>=0,
         troca o valor dele pelo resultado da rotação a direita com ele*/
        if (atual.getBalanceamento() == 2 && atual.getEsq().getBalanceamento() >= 0) {
            atual = rotacaoADireita(atual);
            /* Senão se o nó atual tiver FB=-2 e o filho da direita dele tiver Fb<0,
             troca o valor dele pelo resultado da rotação a esquerda com ele*/
        } else if (atual.getBalanceamento() == -2 && atual.getDir().getBalanceamento() <= 0) { //mudat dps
            atual = rotacaoAEsquerda(atual);
            /*Senão se o nó atual tiver FB=2 e o filho da esquerda dele tiver Fb<0, 
             troca o valor dele pelo resultado da rotação dupla a direita com ele*/
        } else if (atual.getBalanceamento() == 2 && atual.getEsq().getBalanceamento() < 0) {
            atual = rotacaoDuplaDireita(atual);
            /*Senão se o nó atual tiver FB=-2 e o filho da direita dele tiver Fb>0,
             troca o valor dele pelo resultado da rotação dupla a esquerda com ele*/
        } else if (atual.getBalanceamento() == -2 && atual.getDir().getBalanceamento() > 0) {
            atual = rotacaoDuplaEsquerda(atual);
        }
        /*Nessa parte aqui a recursão vai procurar por mais nós desbalanceados*/
        if (atual.getDir() != null) {
            balanceia(atual.getDir());
        }
        if (atual.getEsq() != null) {
            balanceia(atual.getEsq());
        }
        return atual; //Retorna a nova raiz com seus filhotes balanceados    
    }

}
