package st.cri.cde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * The Scribe DTO.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScribeDto { //NOPMD - suppressed TooManyFields

  private String cuit;
  private String apellido;
  private String cargo;
  private String celular;
  private String dias;
  private String domicilio;
  private String email;
  private String estado;
  private String fechaDestitucion;
  private String horario;
  private String imagen;
  private int matricula;
  private boolean mostrarImagen;
  private String nombre;
  private int registro;
  private String sexo;
  private String telefono;
}
