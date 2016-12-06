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
var tabela_service_1 = require('../services/tabela.service');
var BuscarComponent = (function () {
    function BuscarComponent(tabelaService) {
        this.tabelaService = tabelaService;
        this.colunas = [];
        this.chaves = [];
    }
    BuscarComponent.prototype.indiceChanged = function () {
        this.indiceSelecionado = this.indices[this.indiceIndex];
        this.colunas = this.indiceSelecionado.colunas;
    };
    BuscarComponent.prototype.buscar = function () {
        var busca;
        busca = {
            id: this.indiceSelecionado.id,
            buscas: this.chaves
        };
        this.search(busca);
    };
    BuscarComponent.prototype.search = function (b) {
        //this.tabelaService.search(b).then(result => this.resultados = result);
        this.tabelaService.search(b).then(function (result) { return console.log(result); });
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], BuscarComponent.prototype, "indices", void 0);
    BuscarComponent = __decorate([
        core_1.Component({
            selector: 'buscar',
            templateUrl: 'app/components/htmls/buscar.component.html'
        }), 
        __metadata('design:paramtypes', [tabela_service_1.TabelaService])
    ], BuscarComponent);
    return BuscarComponent;
}());
exports.BuscarComponent = BuscarComponent;
//# sourceMappingURL=buscar.component.js.map