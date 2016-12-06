import { Component, Input } from '@angular/core';
import { Tabela } from '../Objects/Tabela'


@Component({
    selector: 'gerar',
    templateUrl: 'app/components/htmls/gerar.component.html'
})

export class GerarComponent{
    @Input()
    tabelas: Tabela[];

    tabelaSelecionada: Tabela;
}