import { Tabela } from '../Objects/Tabela'

export const  tabelas: Tabela[] = [
        {
            id: 1,
            nome: 'primeira tabela ',
            indices: [4,5,6],
            Colunas: [
                {
                nome: 'String',
                tipo: 1,
                tamanho: 65
                }
            ]    
        },
        {
            id: 2,
            nome: 'segunda  ',
            indices: [7,12],
            Colunas: [
                {
                nome: 'inteiro',
                tipo: 0,
                tamanho: 5
                }
            ]    
        }
    ]