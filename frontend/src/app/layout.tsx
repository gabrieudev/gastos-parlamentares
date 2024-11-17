import { Roboto } from "next/font/google";
import "./globals.css";
import Header from "@/components/header";

const roboto = Roboto({
  subsets: ["latin"],
  weight: ["300", "400", "500", "700"],
});

export const metadata = {
  title: "Gastos Parlamentares",
};

import { ReactNode } from "react";
import { Toaster } from "@/components/ui/toaster";

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="pt-br">
      <body className={`${roboto.className} font-sans`}>
        <Header />
        <main>{children}</main>
        <Toaster />
      </body>
    </html>
  );
}
