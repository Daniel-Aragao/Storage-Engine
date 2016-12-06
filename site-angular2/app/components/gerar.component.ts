import { Component, Input } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { Coluna } from '../Objects/Coluna'


@Component({
    selector: 'gerar',
    templateUrl: 'app/components/htmls/gerar.component.html',
    styles: [`
    ul{
        list-style-type: none;
    }`
    ]
})

export class GerarComponent{
    @Input()
    tabelas: Tabela[];
    colunas: Coluna[];

    tabelaIndex: number;
    colunasIndexes: number[];

    indice: Tabela;

    constructor() {
        this.indice = new Tabela();
    }

    tabelaChanged(): void{
        let tabelaSelecionada = this.tabelas[this.tabelaIndex];
        this.colunas = tabelaSelecionada.colunas; 
        this.indice.qtdIndices = tabelaSelecionada.id;
    }

    colunasChanged(): void{
        this.indice.colunas = []
        this.colunasIndexes.forEach(element => {
            this.indice.colunas.push(this.colunas[element]);
        });
    }
    gerarIndice(): void {
        
    }
}