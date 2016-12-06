import { Component, Input } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { Coluna } from '../Objects/Coluna'
import { Busca } from '../Objects/Busca'
import { Resultado } from '../Objects/Resultado'
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

    chaves: string[] = [];

    resultados: Resultado[];

    constructor(private tabelaService: TabelaService) {
    }

    indiceChanged(): void{
        this.indiceSelecionado = this.indices[this.indiceIndex];
        this.colunas = this.indiceSelecionado.colunas;
    }

    buscar(): void{
        let busca: Busca;
        busca = {
            id: this.indiceSelecionado.id,
            buscas: this.chaves
        }

        this.search(busca);
    }
    
    search(b: Busca): void{
        //this.tabelaService.search(b).then(result => this.resultados = result);
        this.tabelaService.search(b).then(result => console.log(result));
    }
    
}