"use client";

import React, { useCallback, useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import { User, DollarSign, FileText, AlertCircle } from "lucide-react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import PaginatedScrollArea from "@/components/deputados-scroll-area";
import DespesasChart from "@/components/despesas-chart";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";

const DashboardPage: React.FC = () => {
  const { toast } = useToast();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedDeputado, setSelectedDeputado] = useState<Deputado | null>(
    null
  );
  const [maiorDespesa, setMaiorDespesa] = useState<Despesa | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSelectedDeputado = (deputado: Deputado) => {
    setSelectedDeputado(deputado);
    setError(null);
  };

  const handleClearDatabase = async () => {
    try {
      await axios.delete("http://localhost:8080/csv");
      setMaiorDespesa(null);
      setSelectedDeputado(null);
      setError(null);
      toast({
        title: "Base de dados excluída com sucesso!",
        description: "Todas as informações foram removidas do banco de dados.",
      });
    } catch (error) {
      toast({
        title: "Erro ao limpar a base de dados",
        description: "Não foi possível limpar a base de dados.",
        variant: "destructive",
      });
      console.error("Erro ao limpar a base de dados:", error);
    }
  };

  const handleMaiorDespesa = useCallback(async () => {
    if (!selectedDeputado?.id) return;

    setIsLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `http://localhost:8080/despesas/maior?idDeputado=${selectedDeputado.id}`
      );
      setMaiorDespesa(response.data);
    } catch (error) {
      setError("Erro ao carregar as despesas. Tente novamente mais tarde.");
      console.error("Erro ao buscar despesas:", error);
    } finally {
      setIsLoading(false);
    }
  }, [selectedDeputado]);

  useEffect(() => {
    if (selectedDeputado) {
      handleMaiorDespesa();
    }
  }, [selectedDeputado, handleMaiorDespesa]);

  return (
    <div className="min-h-screen bg-gray-50 p-6">
      <div className="mx-auto max-w-7xl space-y-6">
        <div className="mb-8">
          <div className="my-10 flex flex-col lg:flex-row lg:items-center lg:justify-between">
            <h1 className="mb-2 text-3xl font-bold text-gray-900">
              Dashboard - Câmara dos Deputados
            </h1>
            <Button
              className="mt-5 lg:mt-0"
              onClick={() => setIsOpen(true)}
              size="lg"
            >
              Excluir dados
            </Button>
            <AlertDialog open={isOpen} onOpenChange={setIsOpen}>
              <AlertDialogContent>
                <AlertDialogHeader>
                  <AlertDialogTitle>Confirmar ação</AlertDialogTitle>
                  <AlertDialogDescription>
                    Tem certeza de que deseja limpar a base de dados? Todas as
                    informações serão excluídas.
                  </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                  <AlertDialogCancel onClick={() => setIsOpen(false)}>
                    Cancelar
                  </AlertDialogCancel>
                  <AlertDialogAction onClick={handleClearDatabase}>
                    Confirmar
                  </AlertDialogAction>
                </AlertDialogFooter>
              </AlertDialogContent>
            </AlertDialog>
          </div>
          <p className="text-gray-600">
            Visualize informações e despesas dos deputados federais
          </p>
        </div>

        {error && (
          <Alert variant="destructive" className="mb-6">
            <AlertCircle className="h-4 w-4" />
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}

        <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
          <Card className="transition-shadow duration-200 shadow-lg hover:shadow-xl">
            <CardHeader className="flex flex-row items-center space-x-4">
              <User className="h-8 w-8 text-blue-500" />
              <div>
                <CardTitle>Deputado Selecionado</CardTitle>
                <CardDescription>Informações do parlamentar</CardDescription>
              </div>
            </CardHeader>
            <CardContent>
              <div
                className={`rounded-lg p-4 ${
                  selectedDeputado ? "bg-blue-50" : "bg-gray-50"
                }`}
              >
                {selectedDeputado ? (
                  <div className="flex items-center space-x-3">
                    <span className="text-lg font-semibold text-blue-700">
                      {selectedDeputado.nome}
                    </span>
                  </div>
                ) : (
                  <p className="italic text-gray-500">
                    Nenhum deputado selecionado
                  </p>
                )}
              </div>
            </CardContent>
          </Card>

          <Card className="transition-shadow duration-200 shadow-lg hover:shadow-xl">
            <CardHeader className="flex flex-row items-center space-x-4">
              <DollarSign className="h-8 w-8 text-green-500" />
              <div>
                <CardTitle>Maior Despesa</CardTitle>
                <CardDescription>Valor mais alto registrado</CardDescription>
              </div>
            </CardHeader>
            <CardContent>
              <div
                className={`rounded-lg p-4 ${
                  maiorDespesa ? "bg-green-50" : "bg-gray-50"
                }`}
              >
                {isLoading ? (
                  <div className="flex items-center space-x-2">
                    <div className="h-4 w-4 animate-spin rounded-full border-2 border-green-500 border-t-transparent" />
                    <span>Carregando...</span>
                  </div>
                ) : selectedDeputado ? (
                  <div className="space-y-2">
                    <p className="text-2xl font-bold text-green-700">
                      {maiorDespesa?.valorLiquido.toLocaleString("pt-BR", {
                        style: "currency",
                        currency: "BRL",
                      })}
                    </p>
                    {maiorDespesa?.urlDocumento && (
                      <Link
                        href={maiorDespesa.urlDocumento}
                        target="_blank"
                        className="inline-flex items-center space-x-2 text-green-600 transition-colors hover:text-green-800"
                      >
                        <FileText className="h-4 w-4" />
                        <span>Ver comprovante</span>
                      </Link>
                    )}
                  </div>
                ) : (
                  <p className="italic text-gray-500">
                    Selecione um deputado para ver sua maior despesa
                  </p>
                )}
              </div>
            </CardContent>
          </Card>
        </div>

        <div className="mt-6 grid grid-cols-1 gap-6 lg:grid-cols-2">
          <div className="rounded-lg bg-white p-4 shadow-lg">
            {selectedDeputado ? (
              <DespesasChart id={selectedDeputado?.id} />
            ) : (
              <div className="flex h-64 items-center justify-center text-gray-500">
                Selecione um deputado para ver o gráfico de despesas
              </div>
            )}
          </div>

          <div className="rounded-lg bg-white p-4 shadow-lg">
            <PaginatedScrollArea
              handleSelectedDeputado={handleSelectedDeputado}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
