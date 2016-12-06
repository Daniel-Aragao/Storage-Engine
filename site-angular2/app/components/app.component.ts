import { Component } from '@angular/core';
import { GerarComponent } from './gerar.component'
import { BuscarComponent } from './buscar.component'
import { TabelaService } from '../services/tabela.service'
import { OnInit } from '@angular/core';
import { Tabela } from '../Objects/Tabela'

@Component({
    selector: 'my-app',
    providers: [TabelaService],
    templateUrl: 'app/components/htmls/app.component.html',
    styleUrls:['app/components/css/app.component.css']
})

export class AppComponent implements OnInit {
    tela: number = 1;
    tabelas: Tabela[];

    constructor(private tabelaService: TabelaService) {
        
    }
    
    mudarTela(id: number): void{
        this.tela = id;
        this.getTabelas(id-1);
        if (this.tela === 1) {
        }
        if (this.tela === 2) {
            //this.getIndices();
        }
    }

    ngOnInit(): void{
        this.getTabelas(this.tela - 1);
        //this.getIndices();
    }

    getTabelas(t : number): void {
        this.tabelaService.getTabelas(t).then(tabelas => this.tabelas = tabelas);
    }

    getIndices(): void {
        this.tabelaService.getTabelas(1).then(tabelas => this.tabelas = tabelas);
    }
    
}