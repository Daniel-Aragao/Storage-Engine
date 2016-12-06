import { Component, Input } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { Coluna } from '../Objects/Coluna'
import { TabelaService } from '../services/tabela.service'

@Component({
    selector: 'buscar',
    templateUrl: 'app/components/htmls/buscar.component.html'
})

export class BuscarComponent {
    @Input()
    indices: Tabela[];

    indiceIndex: number;
    indiceSelecionado: Tabela;
    colunas: Coluna[] = [];

    chaves: String[] = [];

    indiceChanged(): void{
        this.indiceSelecionado = this.indices[this.indiceIndex];
        this.colunas = this.indiceSelecionado.colunas;
    }

}