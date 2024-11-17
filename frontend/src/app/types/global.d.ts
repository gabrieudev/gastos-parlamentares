declare global {
  interface Deputado {
    id: number;
    nome: string;
    uf: string;
    cpf: string;
    partido: string;
    foto?: string;
  }

  interface Despesa {
    id: number;
    deputado: Deputado;
    dataEmissao: string;
    fornecedor: string;
    urlDocumento: string;
    valorLiquido: number;
  }

  interface SomaResponse {
    soma: number;
  }

  interface MediaResponse {
    media: number;
  }
}

export {};
