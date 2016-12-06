import { Tabela } from '../Objects/Tabela'

export const  tabelas: Tabela[] = [
        {
            id: 1,
            nome: 'Fornecedor',
            indices: [4, 5, 6],
            qtdIndices: 0,
            colunas: [
                {
                    nome: 'Nome',
                    tipo: 1,
                    tamanho: 65
                },
                {
                    nome: 'Idade',
                    tipo: 0,
                    tamanho: 5
                }
            ]    
        },
        {
            id: 2,
            nome: 'Autor ',
            indices: [7, 12],
            qtdIndices: 7,
            colunas: [
                {
                    nome: 'Codigo autor',
                    tipo: 0,
                    tamanho: 5
                },
                {
                    nome: 'Nome autor',
                    tipo: 1,
                    tamanho: 25
                }
            ]    
        }
    ]