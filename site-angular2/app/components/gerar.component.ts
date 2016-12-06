import { Component, Input } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { Coluna } from '../Objects/Coluna'
import { TabelaService } from '../services/tabela.service'


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
    colunasIndexes: number[] = [];
    tabelaSelecionada: Tabela;

    indice: Tabela;
    ordem: number = 0;

    constructor(private tabelaService: TabelaService) {
        this.indice = new Tabela();
    }

    tabelaChanged(): void{
        this.tabelaSelecionada = this.tabelas[this.tabelaIndex];
        this.colunas = this.tabelaSelecionada.colunas; 
        this.indice.qtdIndices = this.tabelaSelecionada.id;
    }

    gerarIndice(): void {
        this.indice.colunas = [];
        let self = this;
        this.colunasIndexes.forEach(function(v, i){
            if (v) {
                self.indice.colunas.push(self.colunas[i]);                
            }
        });
        console.log(this.colunasIndexes);
        console.log(this.indice);

        this.tabelaService.create(this.indice).then(result => this.ordem = result);
    }
}