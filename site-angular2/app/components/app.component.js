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
var AppComponent = (function () {
    function AppComponent(tabelaService) {
        this.tabelaService = tabelaService;
        this.tela = 1;
    }
    AppComponent.prototype.mudarTela = function (id) {
        this.tela = id;
        this.getTabelas(id - 1);
        if (this.tela === 1) {
        }
        if (this.tela === 2) {
        }
    };
    AppComponent.prototype.ngOnInit = function () {
        this.getTabelas(this.tela - 1);
        //this.getIndices();
    };
    AppComponent.prototype.getTabelas = function (t) {
        var _this = this;
        this.tabelaService.getTabelas(t).then(function (tabelas) { return _this.tabelas = tabelas; });
    };
    AppComponent.prototype.getIndices = function () {
        var _this = this;
        this.tabelaService.getTabelas(1).then(function (tabelas) { return _this.tabelas = tabelas; });
    };
    AppComponent = __decorate([
        core_1.Component({
            selector: 'my-app',
            providers: [tabela_service_1.TabelaService],
            templateUrl: 'app/components/htmls/app.component.html',
            styleUrls: ['app/components/css/app.component.css']
        }), 
        __metadata('design:paramtypes', [tabela_service_1.TabelaService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map