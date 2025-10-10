package com.senac.av1web.gustavo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="folha_pagamento")
public class FolhaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="folha_pagamento_id")
    private Integer id;
    @Column(name="folha_pagamento_mes")
    private int mes;
    @Column(name="folha_pagamento_ano")
    private int ano;
    @Column(name="folha_pagamento_salario")
    private int salario;




}
