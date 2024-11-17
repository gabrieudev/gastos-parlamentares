import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import axios from "axios";
import { Loader2 } from "lucide-react";
import React, { useEffect, useState } from "react";
import {
  Area,
  AreaChart,
  CartesianGrid,
  Line,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

interface DeputadoProps {
  id: number;
}

interface DespesaMensal {
  mes: string;
  total: number;
  mediaMovel: number;
}

const ExpenseChart: React.FC<DeputadoProps> = ({ id }) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [despesasMensais, setDespesasMensais] = useState<DespesaMensal[]>([]);
  const [nomeDeputado, setNomeDeputado] = useState<string>("");
  const [totalAnual, setTotalAnual] = useState<number>(0);
  const [mediaAnual, setMediaAnual] = useState<number>(0);

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(value);
  };

  const calcularMediaMovel = (despesas: DespesaMensal[]) => {
    return despesas.map((despesa, index) => {
      if (index < 2) return despesa.total;
      const mediaMovel =
        (despesas[index - 2].total +
          despesas[index - 1].total +
          despesa.total) /
        3;
      return mediaMovel;
    });
  };

  useEffect(() => {
    const meses = [
      "Janeiro",
      "Fevereiro",
      "Março",
      "Abril",
      "Maio",
      "Junho",
      "Julho",
      "Agosto",
      "Setembro",
      "Outubro",
      "Novembro",
      "Dezembro",
    ];

    const fetchDespesas = async () => {
      try {
        const primeiraResposta = await axios.get<Despesa>(
          `http://localhost:8080/despesas/${id}`
        );

        if (!primeiraResposta.data || !primeiraResposta.data.dataEmissao) {
          throw new Error("Dados da primeira despesa não encontrados");
        }

        const primeiraData = new Date(primeiraResposta.data.dataEmissao);
        const ano = primeiraData.getFullYear();
        setNomeDeputado(primeiraResposta.data.deputado.nome);

        const dataInicio = `${ano}-01-01T00:00`;
        const dataFim = `${ano}-12-31T23:59`;

        let todasDespesas: Despesa[] = [];
        let pagina = 0;

        while (true) {
          const response = await axios.get<{
            content: Despesa[];
            last: boolean;
          }>(`http://localhost:8080/despesas/data`, {
            params: {
              idDeputado: id,
              dataInicio,
              dataFim,
              page: pagina,
              size: 100,
            },
          });

          if (!response.data.content) {
            throw new Error("Formato de resposta inválido");
          }

          todasDespesas = [...todasDespesas, ...response.data.content];

          if (response.data.last || !response.data.content.length) break;
          pagina++;
        }

        const despesasPorMes = meses.map((mes, index) => {
          const despesasDoMes = todasDespesas.filter((despesa) => {
            const data = new Date(despesa.dataEmissao);
            return data.getMonth() === index;
          });

          const total = despesasDoMes.reduce(
            (acc, despesa) => acc + despesa.valorLiquido,
            0
          );

          return {
            mes,
            total,
            mediaMovel: 0,
          };
        });

        const mediasMoveis = calcularMediaMovel(despesasPorMes);
        const despesasComMediaMovel = despesasPorMes.map((despesa, index) => ({
          ...despesa,
          mediaMovel: mediasMoveis[index],
        }));

        const totalAno = despesasPorMes.reduce(
          (acc, mes) => acc + mes.total,
          0
        );
        const mediaAno = totalAno / 12;

        setTotalAnual(totalAno);
        setMediaAnual(mediaAno);
        setDespesasMensais(despesasComMediaMovel);
        setLoading(false);
      } catch (err) {
        console.error("Erro completo:", err);
        setError(
          err instanceof Error ? err.message : "Erro ao carregar despesas"
        );
        setLoading(false);
      }
    };

    fetchDespesas();
  }, [id]);

  if (loading) {
    return (
      <Card className="w-full h-96 flex items-center justify-center">
        <Loader2 className="h-8 w-8 animate-spin" />
      </Card>
    );
  }

  if (error) {
    return (
      <Card className="w-full h-96 flex items-center justify-center">
        <p className="text-red-500">Erro: {error}</p>
      </Card>
    );
  }

  return (
    <Card className="w-full">
      <CardHeader>
        <CardTitle>Despesas Mensais - {nomeDeputado}</CardTitle>
        <CardDescription>
          Total Anual: {formatCurrency(totalAnual)} | Média Mensal:{" "}
          {formatCurrency(mediaAnual)}
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="h-[400px]">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart
              data={despesasMensais}
              margin={{
                top: 10,
                right: 30,
                left: 20,
                bottom: 5,
              }}
            >
              <defs>
                <linearGradient id="colorTotal" x1="0" y1="0" x2="0" y2="1">
                  <stop
                    offset="5%"
                    stopColor="hsl(var(--primary))"
                    stopOpacity={0.3}
                  />
                  <stop
                    offset="95%"
                    stopColor="hsl(var(--primary))"
                    stopOpacity={0}
                  />
                </linearGradient>
              </defs>
              <CartesianGrid
                strokeDasharray="3 3"
                className="stroke-muted/30"
              />
              <XAxis
                dataKey="mes"
                className="text-xs font-medium"
                tick={{ fill: "hsl(var(--foreground))" }}
              />
              <YAxis
                className="text-xs font-medium"
                tickFormatter={formatCurrency}
                tick={{ fill: "hsl(var(--foreground))" }}
              />
              <Tooltip
                formatter={(value: number) => [formatCurrency(value), "Total"]}
                contentStyle={{
                  backgroundColor: "hsl(var(--card))",
                  border: "1px solid hsl(var(--border))",
                  borderRadius: "6px",
                  padding: "8px",
                }}
                itemStyle={{ color: "hsl(var(--foreground))" }}
                labelStyle={{ color: "hsl(var(--foreground))" }}
              />
              <Area
                type="monotone"
                dataKey="total"
                stroke="hsl(var(--primary))"
                fill="url(#colorTotal)"
                strokeWidth={2}
                dot={{
                  fill: "hsl(var(--primary))",
                  r: 4,
                }}
                activeDot={{
                  r: 6,
                  fill: "hsl(var(--primary))",
                  stroke: "hsl(var(--background))",
                  strokeWidth: 2,
                }}
                animationDuration={1500}
                animationEasing="ease-in-out"
              />
              <Line
                type="monotone"
                dataKey="mediaMovel"
                stroke="hsl(var(--destructive))"
                strokeWidth={2}
                dot={false}
                strokeDasharray="5 5"
                animationDuration={1500}
                animationEasing="ease-in-out"
              />
            </AreaChart>
          </ResponsiveContainer>
        </div>
      </CardContent>
    </Card>
  );
};

export default ExpenseChart;
