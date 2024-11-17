"use client";

import { Button } from "@/components/ui/button";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
} from "@/components/ui/pagination";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import axios from "axios";
import { Loader2, User } from "lucide-react";
import { useCallback, useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./ui/card";
import { Input } from "./ui/input";

type PaginatedScrollAreaProps = {
  handleSelectedDeputado: (deputado: Deputado) => void;
};

const PaginatedScrollArea: React.FC<PaginatedScrollAreaProps> = ({
  handleSelectedDeputado,
}) => {
  const [deputados, setDeputados] = useState<Deputado[]>([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [totalPages, setTotalPages] = useState(1);
  const [searchTerm, setSearchTerm] = useState("");

  const fetchDeputadoPhoto = async (nome: string) => {
    try {
      const response = await axios.get(
        `https://dadosabertos.camara.leg.br/api/v2/deputados?nome=${encodeURIComponent(
          nome
        )}`,
        {
          headers: {
            Accept: "application/json",
          },
        }
      );
      return response.data.dados[0]?.urlFoto;
    } catch (error) {
      console.error("Erro ao buscar foto do deputado:", error);
      return null;
    }
  };

  const fetchDeputados = useCallback(async (pageNumber: number) => {
    setLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:8080/deputados?page=${pageNumber}&size=15`
      );
      const newDeputados = response.data.content;

      // Buscar fotos para cada deputado
      const deputadosComFoto = await Promise.all(
        newDeputados.map(async (deputado: Deputado) => {
          const foto = await fetchDeputadoPhoto(deputado.nome);
          return { ...deputado, foto };
        })
      );

      setDeputados(deputadosComFoto);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Erro ao carregar dados:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchDeputados(page);
  }, [page, fetchDeputados]);

  const handlePageChange = (newPage: number) => {
    setPage(newPage);
    fetchDeputados(newPage);
  };

  const handleDeputadoSearch = async (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const search = event.target.value;
    setSearchTerm(search);

    if (search.length < 3) {
      fetchDeputados(page);
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/deputados/buscar?nome=${search}&page=0&size=15`
      );
      const newDeputados = response.data.content;

      // Buscar fotos para os deputados encontrados na pesquisa
      const deputadosComFoto = await Promise.all(
        newDeputados.map(async (deputado: Deputado) => {
          const foto = await fetchDeputadoPhoto(deputado.nome);
          return { ...deputado, foto };
        })
      );

      setDeputados(deputadosComFoto);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Erro ao carregar dados:", error);
    }
  };

  return (
    <div className="max-w-3xl mx-auto p-4 space-y-6">
      <div className="relative">
        <Input
          type="text"
          placeholder="Pesquisar deputado por nome..."
          className="w-full pl-4 pr-10 py-2 rounded-lg border border-gray-200 focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
          onChange={handleDeputadoSearch}
          value={searchTerm}
        />
        {loading && (
          <div className="absolute right-3 top-2.5">
            <Loader2 className="h-5 w-5 animate-spin text-gray-400" />
          </div>
        )}
      </div>

      <ScrollArea className="h-[600px] rounded-lg border border-gray-100 shadow-sm">
        <div className="p-4 space-y-3">
          {deputados.map((deputado) => (
            <button
              key={deputado.id}
              onClick={() => handleSelectedDeputado(deputado)}
              className="w-full transition-transform hover:scale-[1.02] focus:outline-none focus:ring-2 focus:ring-blue-500 rounded-lg"
            >
              <Card className="border hover:border-blue-200 hover:bg-blue-50/50 transition-colors duration-200">
                <CardHeader className="pb-2">
                  <div className="flex items-center gap-4">
                    <Avatar className="h-12 w-12">
                      <AvatarImage src={deputado.foto} alt={deputado.nome} />
                      <AvatarFallback>
                        <User className="h-6 w-6" />
                      </AvatarFallback>
                    </Avatar>
                    <CardTitle className="text-lg font-semibold text-gray-900">
                      {deputado.nome}
                    </CardTitle>
                  </div>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-3 gap-2 text-sm text-gray-600">
                    <CardDescription className="flex items-center gap-1">
                      <span className="font-medium">CPF:</span>
                      <span>{deputado.cpf}</span>
                    </CardDescription>
                    <CardDescription className="flex items-center gap-1">
                      <span className="font-medium">Estado:</span>
                      <span>{deputado.uf}</span>
                    </CardDescription>
                    <CardDescription className="flex items-center gap-1">
                      <span className="font-medium">Partido:</span>
                      <span>{deputado.partido}</span>
                    </CardDescription>
                  </div>
                </CardContent>
              </Card>
            </button>
          ))}
          {loading && (
            <div className="flex justify-center py-8">
              <Loader2 className="h-8 w-8 animate-spin text-blue-500" />
            </div>
          )}
          {!loading && deputados.length === 0 && (
            <div className="text-center py-8 text-gray-500">
              Nenhum deputado encontrado
            </div>
          )}
        </div>
      </ScrollArea>

      <Pagination className="flex justify-center">
        <PaginationContent>
          <Button
            variant="outline"
            className={`${
              page === 0 ? "opacity-50 pointer-events-none" : "hover:bg-blue-50"
            }`}
            onClick={() => handlePageChange(Math.max(0, page - 1))}
          >
            Anterior
          </Button>
          <PaginationItem className="px-4 py-2 font-medium text-sm">
            Página {page + 1} de {totalPages}
          </PaginationItem>
          <Button
            variant="outline"
            className={`${
              page === totalPages - 1
                ? "opacity-50 pointer-events-none"
                : "hover:bg-blue-50"
            }`}
            onClick={() => handlePageChange(Math.min(totalPages - 1, page + 1))}
          >
            Próxima
          </Button>
        </PaginationContent>
      </Pagination>
    </div>
  );
};

export default PaginatedScrollArea;
