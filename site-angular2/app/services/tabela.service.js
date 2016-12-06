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
var mock_tabelas_1 = require('../components/mock-tabelas');
var http_1 = require('@angular/http');
require('rxjs/add/operator/toPromise');
var TabelaService = (function () {
    function TabelaService(http) {
        this.http = http;
        this.url = '';
    }
    TabelaService.prototype.getTabelas = function (tipo) {
        return Promise.resolve(mock_tabelas_1.tabelas);
        /*
        return this.http.get(this.url)
            .toPromise()
            .then(response => response.json().data as Tabela[])
            .catch(this.handleError);
        */
    };
    TabelaService.prototype.handleError = function (error) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    };
    TabelaService.prototype.create = function (indice) {
        return Promise.resolve(10);
        /*
        return this.http
            .post(this.url, JSON.stringify(indice))
            .toPromise()
            .then(res => res.json().data)
            .catch(this.handleError)
        */
    };
    TabelaService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], TabelaService);
    return TabelaService;
}());
exports.TabelaService = TabelaService;
//# sourceMappingURL=tabela.service.js.map