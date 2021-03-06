
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Medicamento;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

public class MedicamentoDAO implements Serializable{
   private String mensagem = "";
   private EntityManager em;
   
   public MedicamentoDAO(){
       em = EntityManagerUtil.getEntityManager();
   }
   
   public List<Medicamento> getLista() {
        return getEm().createQuery("from Medicamento order by nome").getResultList();
    }

    public boolean salvar(Medicamento obj) {
        try {
            getEm().getTransaction().begin();
            if (obj.getId() == null) {
                getEm().persist(obj);
            } else {
                getEm().merge(obj);
            }
            getEm().getTransaction().commit();
            setMensagem("Objeto persistido com sucesso");
            return true;
        } catch (Exception e) {
            if (getEm().getTransaction().isActive() == false) {
                getEm().getTransaction().begin();
            }
            getEm().getTransaction().rollback();
            setMensagem("Erro ao persistir objeto: " + Util.getMensagemErro(e));
            return false;
        }
    }

    public boolean remover(Medicamento obj) {
        try {
            getEm().getTransaction().begin();
            getEm().remove(obj);
            getEm().getTransaction().commit();
            setMensagem("Objeto persistido com sucesso");
            return true;
        } catch (Exception e) {
            if (getEm().getTransaction().isActive() == false) {
                getEm().getTransaction().begin();
            }
            getEm().getTransaction().rollback();
            setMensagem("Erro ao remover objeto: " + Util.getMensagemErro(e));
            return false;
        }
    }
    
    public Medicamento localizar(Integer id) {
        return getEm().find(Medicamento.class, id);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
   
}
