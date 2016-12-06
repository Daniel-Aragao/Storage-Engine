import { Injectable } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { OnInit } from '@angular/core';
import { tabelas} from '../components/mock-tabelas'

@Injectable()
export class TabelaService {
    getTabelas(): Promise<Tabela[]>{
        return Promise.resolve(tabelas);
    }
}