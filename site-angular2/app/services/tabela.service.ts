import { Injectable } from '@angular/core';
import { Tabela } from '../Objects/Tabela'
import { OnInit } from '@angular/core';
import { tabelas } from '../components/mock-tabelas'
import { Busca } from '../Objects/Busca'
import { Resultado } from '../Objects/Resultado'
import { URLSearchParams } from '@angular/http'

import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class TabelaService {
    private url = 'http://localhost:8080/Storage-Engine-Web/api';
    private headers = new Headers({ 'Content-Type': 'application/json' })//, "Access-Control-Allow-Origin": "http://localhost:3000", "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE" });
    
    constructor(private http: Http) { }

    getTabelas(tipo: number): Promise<Tabela[]>{
        // return Promise.resolve(tabelas);

        let params: URLSearchParams = new URLSearchParams();
        params.set('tipo', tipo+"");
        
        return this.http.get(this.url + "/tabelas/getTabelas", { search: params })
            // headers: this.headers})
            .toPromise()
            .then(response => response.json().tabelas as Tabela[])
            .catch(this.handleError);
        
    }
    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    create(indice: Tabela): Promise<number>{
        console.log(JSON.stringify(indice));
        //return Promise.resolve(10);
        
        return this.http
            .post(this.url + "/indices/getOrdem",
            JSON.stringify(indice), {headers: this.headers})
            .toPromise()
            .then(res => res.json().ordem)
            .catch(this.handleError)
        
        
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

    search(b: Busca): Promise<Resultado[]>{
        return Promise.resolve(37);
        /*
        return this.http.get(this.url)
            .toPromise()
            .then(response => response.json().data as Resultado[])
            .catch(this.handleError);
        */
    }
}