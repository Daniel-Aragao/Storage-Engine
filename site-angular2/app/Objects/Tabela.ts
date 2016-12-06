import { Coluna } from './Coluna';

export class Tabela{
    id: number;
    nome: String;
    indices: number[];
    colunas: Coluna[];
    qtdIndices: number;
}