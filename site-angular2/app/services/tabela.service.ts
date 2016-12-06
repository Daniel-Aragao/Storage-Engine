import { Injectable } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { OnInit } from '@angular/core';
import { tabelas } from '../components/mock-tabelas'

import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class TabelaService {
    private url = '';

    constructor(private http: Http) { }

    getTabelas(tipo: number): Promise<Tabela[]>{
        return Promise.resolve(tabelas);
        /*
        return this.http.get(this.url)
            .toPromise()
            .then(response => response.json().data as Tabela[])
            .catch(this.handleError);
        */
    }
    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    create(indice: Tabela): Promise<number>{
        return Promise.resolve(10);
        /*
        return this.http
            .post(this.url, JSON.stringify(indice))
            .toPromise()
            .then(res => res.json().data)
            .catch(this.handleError)
        */
    }

    /*
    create(name: string): Promise<Hero> {
    return this.http
        .post(this.heroesUrl, JSON.stringify({name: name}), {headers: this.headers})
        .toPromise()
        .then(res => res.json().data)
        .catch(this.handleError);
    }
    */
}