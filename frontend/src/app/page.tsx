"use client";

import { useState, ChangeEvent } from "react";
import { useRouter } from "next/navigation";
import axios, { AxiosError } from "axios";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2 } from "lucide-react";

type UF =
  | "AC"
  | "AL"
  | "AP"
  | "AM"
  | "BA"
  | "CE"
  | "DF"
  | "ES"
  | "GO"
  | "MA"
  | "MT"
  | "MS"
  | "MG"
  | "PA"
  | "PB"
  | "PR"
  | "PE"
  | "PI"
  | "RJ"
  | "RN"
  | "RS"
  | "RO"
  | "RR"
  | "SC"
  | "SP"
  | "SE"
  | "TO"
  | "TODOS";

interface UploadState {
  file: File | null;
  uf: UF;
  isLoading: boolean;
  error: string;
}

const UploadPage: React.FC = () => {
  const router = useRouter();
  const [state, setState] = useState<UploadState>({
    file: null,
    uf: "TODOS",
    isLoading: false,
    error: "",
  });

  const estados: UF[] = [
    "AC",
    "AL",
    "AP",
    "AM",
    "BA",
    "CE",
    "DF",
    "ES",
    "GO",
    "MA",
    "MT",
    "MS",
    "MG",
    "PA",
    "PB",
    "PR",
    "PE",
    "PI",
    "RJ",
    "RN",
    "RS",
    "RO",
    "RR",
    "SC",
    "SP",
    "SE",
    "TO",
  ];

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>): void => {
    const selectedFile = e.target.files?.[0];
    if (selectedFile && selectedFile.type === "text/csv") {
      setState((prev) => ({ ...prev, file: selectedFile, error: "" }));
    } else {
      setState((prev) => ({
        ...prev,
        error: "Por favor, selecione um arquivo CSV válido",
        file: null,
      }));
    }
  };

  const handleUpload = async (): Promise<void> => {
    if (!state.file) {
      setState((prev) => ({
        ...prev,
        error: "Por favor, selecione um arquivo para upload",
      }));
      return;
    }

    setState((prev) => ({ ...prev, isLoading: true, error: "" }));

    const formData = new FormData();
    formData.append("arquivo", state.file);

    try {
      const url =
        state.uf !== "TODOS"
          ? `http://localhost:8080/csv?uf=${state.uf}`
          : "http://localhost:8080/csv";

      await axios.post(url, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      setTimeout(() => {
        router.push("/dashboard");
      }, 1000);
    } catch (err) {
      const error = err as AxiosError;
      setState((prev) => ({
        ...prev,
        error: `Erro ao fazer upload do arquivo: ${error.message}`,
        progress: 0,
      }));
    } finally {
      setState((prev) => ({ ...prev, isLoading: false }));
    }
  };

  const handleUfChange = (value: UF): void => {
    setState((prev) => ({ ...prev, uf: value }));
  };

  return (
    <div className="absolute mt-10 w-full max-w-md min-h-screen p-6 px-4 py-12 transform -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2 sm:px-6 lg:px-8">
      <Card className="max-w-md p-6 mx-auto space-y-6">
        <div className="space-y-2 text-center">
          <h1 className="text-2xl font-bold">Gastos Parlamentares</h1>
          <p className="text-gray-500">
            Selecione um arquivo CSV com os dados dos deputados
          </p>
        </div>

        <div className="space-y-4">
          <div className="space-y-2">
            <label className="block text-sm font-medium text-gray-700">
              Estado (opcional)
            </label>
            <Select value={state.uf} onValueChange={handleUfChange}>
              <SelectTrigger>
                <SelectValue placeholder="Selecione um estado" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="TODOS">Todos os estados</SelectItem>
                {estados.map((estado) => (
                  <SelectItem key={estado} value={estado}>
                    {estado}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <label className="block text-sm font-medium text-gray-700">
              Arquivo CSV
            </label>
            <Input
              type="file"
              accept=".csv"
              onChange={handleFileChange}
              disabled={state.isLoading}
              className="w-full"
            />
          </div>

          {state.error && (
            <Alert variant="destructive">
              <AlertDescription>{state.error}</AlertDescription>
            </Alert>
          )}

          {state.isLoading && (
            <div className="space-y-2">
              <p className="text-sm text-center text-gray-500">
                Aguarde, pode levar alguns minutos...
              </p>
            </div>
          )}

          <Button
            onClick={handleUpload}
            disabled={!state.file || state.isLoading}
            className="w-full"
          >
            {state.isLoading ? (
              <>
                <Loader2 className="w-4 h-4 mr-2 animate-spin" />
                Processando
              </>
            ) : (
              "Fazer Upload"
            )}
          </Button>
        </div>
      </Card>
    </div>
  );
};

export default UploadPage;
