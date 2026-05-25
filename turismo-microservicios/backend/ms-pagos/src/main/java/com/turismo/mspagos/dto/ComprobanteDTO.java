package com.turismo.mspagos.dto;

import com.turismo.mspagos.model.TipoComprobante;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComprobanteDTO {

    private Long id;

    @NotBlank(message = "La serie del comprobante es obligatoria")
    @Size(max = 10, message = "La serie no debe superar los 10 caracteres")
    private String serie;

    @NotBlank(message = "El número del comprobante es obligatorio")
    @Size(max = 20, message = "El número no debe superar los 20 caracteres")
    private String numero;

    @NotNull(message = "El tipo de comprobante es obligatorio")
    private TipoComprobante tipo;

    private LocalDateTime fechaEmision;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    private BigDecimal montoTotal;
}
