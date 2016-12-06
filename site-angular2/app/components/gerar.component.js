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
var Tabela_1 = require('../Objects/Tabela');
var GerarComponent = (function () {
    function GerarComponent() {
        this.indice = new Tabela_1.Tabela();
    }
    GerarComponent.prototype.tabelaChanged = function () {
        var tabelaSelecionada = this.tabelas[this.tabelaIndex];
        this.colunas = tabelaSelecionada.colunas;
        this.indice.qtdIndices = tabelaSelecionada.id;
    };
    GerarComponent.prototype.colunasChanged = function () {
        var _this = this;
        this.indice.colunas = [];
        this.colunasIndexes.forEach(function (element) {
            _this.indice.colunas.push(_this.colunas[element]);
        });
    };
    GerarComponent.prototype.gerarIndice = function () {
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], GerarComponent.prototype, "tabelas", void 0);
    GerarComponent = __decorate([
        core_1.Component({
            selector: 'gerar',
            templateUrl: 'app/components/htmls/gerar.component.html',
            styles: ["\n    ul{\n        list-style-type: none;\n    }"
            ]
        }), 
        __metadata('design:paramtypes', [])
    ], GerarComponent);
    return GerarComponent;
}());
exports.GerarComponent = GerarComponent;
//# sourceMappingURL=gerar.component.js.map