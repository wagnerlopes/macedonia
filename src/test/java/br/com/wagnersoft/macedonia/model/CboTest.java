package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class CboTest {

  @Test
  void testGettersAndSetters() {
    Cbo cbo = new Cbo();
    cbo.setCodigo("1234");
    cbo.setDescricao("XXX");
    assertEquals("1234", cbo.getCodigo());
    assertEquals("XXX", cbo.getDescricao());
  }

  @Test
  void testEqualsAndHashCode() {
    Cbo b1 = new Cbo();
    b1.setCodigo("1234");
    b1.setDescricao("XXX");
    
    Cbo b2 = new Cbo();
    b2.setCodigo("1234");
    b2.setDescricao("XXX");

    Cbo dif = new Cbo();
    dif.setCodigo("1");
    dif.setDescricao("X");
    
    assertThat(b1).isEqualTo(b2);
    assertThat(b1.hashCode()).isEqualTo(b2.hashCode());
    
    assertNotEquals(b1, dif);
    assertNotEquals(b1.hashCode(), dif.hashCode());

    assertNotEquals(null, b1);
    assertNotEquals("Uma String qualquer", b1);
    
  }

}
