import Link from "next/link";

const Header: React.FC = () => {
  return (
    <header className="flex items-center justify-between">
      <h1 className="font-bold m-4">Gastos Parlamentares</h1>
      <nav>
        <ul
          className="flex items-center justify-between gap-5 m-3
        "
        >
          <li className="font-semibold hover:text-gray-400">
            <Link href="/">Página inicial</Link>
          </li>
          <li className="font-semibold hover:text-gray-400">
            <Link href="/dashboard">Dashboard</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
