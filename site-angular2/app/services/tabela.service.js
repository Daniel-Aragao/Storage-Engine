"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
var http_2 = require('@angular/http');
require('rxjs/add/operator/toPromise');
var TabelaService = (function () {
    function TabelaService(http) {
        this.http = http;
        this.url = 'http://localhost:8080/Storage-Engine-Web/api';
        this.headers = new http_2.Headers({ 'Content-Type': 'application/json' }); //, "Access-Control-Allow-Origin": "http://localhost:3000", "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE" });
    }
    TabelaService.prototype.getTabelas = function (tipo) {
        // return Promise.resolve(tabelas);
        var params = new http_1.URLSearchParams();
        params.set('tipo', tipo + "");
        return this.http.get(this.url + "/tabelas/getTabelas", { search: params })
            .toPromise()
            .then(function (response) { return response.json().tabelas; })
            .catch(this.handleError);
    };
    TabelaService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    TabelaService.prototype.create = function (indice) {
        console.log(JSON.stringify(indice));
        //return Promise.resolve(10);
        return this.http
            .post(this.url + "/indices/getOrdem", JSON.stringify(indice), { headers: this.headers })
            .toPromise()
            .then(function (res) { return res.json().ordem; })
            .catch(this.handleError);
    };
    TabelaService.prototype.search = function (b) {
        //return Promise.resolve(37);
        return this.http.post(this.url + "/busca/getBusca", JSON.stringify(b), { headers: this.headers })
            .toPromise()
            .then(function (response) { return response.json(); })
            .catch(this.handleError);
    };
    TabelaService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_2.Http])
    ], TabelaService);
    return TabelaService;
}());
exports.TabelaService = TabelaService;
//# sourceMappingURL=tabela.service.js.map