import React, { useState, createContext, useContext, useEffect } from 'react';

// --- 1. CONTEXTO DE DADOS (Gestão de Estado Global) ---
const AppContext = createContext();

const AppProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [artistas, setArtistas] = useState([]);
    const [loading, setLoading] = useState(false);

    // Função de Login Simulada (Integração com o Token JWT)
    const loginAction = async (username, password) => {
        setLoading(true);
        // Simula uma chamada de API
        setTimeout(() => {
            if (username === 'admin' && password === 'admin123') {
                setUser({ username, role: 'ADMIN' });
                localStorage.setItem('token', 'fake-jwt-token-seplag');
            } else {
                alert("Credenciais inválidas! Use admin / admin123");
            }
            setLoading(false);
        }, 800);
    };

    const logout = () => {
        setUser(null);
        localStorage.removeItem('token');
    };

    return (
        <AppContext.Provider value={{ user, artistas, setArtistas, loading, loginAction, logout }}>
            {children}
        </AppContext.Provider>
    );
};

const useApp = () => useContext(AppContext);

// --- 2. COMPONENTES DE INTERFACE (UI) ---

const ArtistaCard = ({ artista }) => (
    <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-all duration-300 group">
        <div className="flex items-center justify-between mb-4">
            <div className="bg-blue-50 p-3 rounded-xl text-blue-600 group-hover:bg-blue-600 group-hover:text-white transition-colors">
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2z" />
                </svg>
            </div>
            <span className="text-[10px] font-bold uppercase tracking-wider text-blue-500 bg-blue-50 px-2 py-1 rounded">Sénior Cloud</span>
        </div>
        <h3 className="text-lg font-bold text-gray-800">{artista.nome}</h3>
        <p className="text-gray-500 text-xs mt-1 italic">
            {artista.albunsExemplo || `${artista.albunsCount} álbuns cadastrados`}
        </p>
        <div className="mt-4 pt-4 border-t border-gray-50 flex justify-between items-center">
            <button className="text-sm font-semibold text-blue-600 hover:text-blue-800 transition-colors">Ver Detalhes</button>
            <div className="flex gap-3">
                <button className="text-gray-400 hover:text-blue-600">
                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"/></svg>
                </button>
            </div>
        </div>
    </div>
);

// --- 3. ECRÃS (PAGES) ---

const Dashboard = () => {
    const { user, logout, artistas, setArtistas } = useApp();

    useEffect(() => {
        // Dados para demonstração conforme o edital
        setArtistas([
            { id: 1, nome: "Serj Tankian", albunsExemplo: "Harakiri, Black Blooms", albunsCount: 2 },
            { id: 2, nome: "Mike Shinoda", albunsExemplo: "Post Traumatic", albunsCount: 1 },
            { id: 3, nome: "Michel Teló", albunsExemplo: "Bem Sertanejo", albunsCount: 1 },
            { id: 4, nome: "Guns N' Roses", albunsExemplo: "Use Your Illusion I", albunsCount: 1 }
        ]);
    }, [setArtistas]);

    return (
        <div className="min-h-screen bg-gray-50">
            <nav className="bg-white border-b border-gray-200 px-6 py-4 flex justify-between items-center sticky top-0 z-10 shadow-sm">
                <div className="flex items-center gap-3">
                    <div className="w-10 h-10 bg-blue-800 rounded-xl flex items-center justify-center text-white font-black text-xl">S</div>
                    <div>
                        <h1 className="text-lg font-black text-gray-900 leading-tight">SEPLAG</h1>
                        <p className="text-[10px] font-bold text-blue-600 uppercase tracking-widest">Gestão Musical</p>
                    </div>
                </div>
                
                <div className="flex items-center gap-5">
                    <div className="text-right hidden sm:block">
                        <p className="text-sm font-bold text-gray-800">{user?.username}</p>
                        <p className="text-[10px] text-gray-400 font-bold uppercase">Acesso Sénior</p>
                    </div>
                    <button onClick={logout} className="p-2 hover:bg-red-50 text-gray-400 hover:text-red-600 rounded-lg transition-all">
                        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" /></svg>
                    </button>
                </div>
            </nav>

            <main className="p-6 max-w-7xl mx-auto">
                <header className="flex justify-between items-center mb-10">
                    <div>
                        <h2 className="text-3xl font-black text-gray-900 tracking-tight">Catálogo de Artistas</h2>
                        <p className="text-gray-500 font-medium">Gestão de discografia conforme requisitos do edital.</p>
                    </div>
                    <button className="bg-blue-800 text-white px-6 py-3 rounded-2xl font-bold shadow-lg hover:bg-blue-700 transition-all">
                        + Novo Artista
                    </button>
                </header>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                    {artistas.map(art => (
                        <ArtistaCard key={art.id} artista={art} />
                    ))}
                </div>
            </main>
        </div>
    );
};

// Componente de Login importado logicamente
const LoginScreen = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { loginAction, loading } = useApp();

    const handleSubmit = (e) => {
        e.preventDefault();
        loginAction(username, password);
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100 p-6">
            <div className="w-full max-w-md bg-white rounded-3xl shadow-xl overflow-hidden border border-gray-200">
                <div className="p-10">
                    <div className="mb-10 text-center">
                        <div className="w-16 h-16 bg-blue-800 rounded-2xl mx-auto mb-4 flex items-center justify-center shadow-lg">
                            <span className="text-white text-3xl font-black">S</span>
                        </div>
                        <h1 className="text-2xl font-bold text-gray-900">SEPLAG Sênior</h1>
                        <p className="text-sm text-gray-500 font-medium">Gestão de Artistas e Álbuns</p>
                    </div>

                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div className="space-y-1">
                            <label className="text-xs font-bold text-gray-400 uppercase tracking-widest ml-1">Utilizador</label>
                            <input 
                                type="text" 
                                className="w-full p-4 bg-gray-50 border border-gray-200 rounded-2xl outline-none focus:ring-2 focus:ring-blue-600"
                                placeholder="Utilizador (admin)"
                                value={username}
                                onChange={e => setUsername(e.target.value)}
                            />
                        </div>
                        <div className="space-y-1">
                            <label className="text-xs font-bold text-gray-400 uppercase tracking-widest ml-1">Palavra-passe</label>
                            <input 
                                type="password" 
                                className="w-full p-4 bg-gray-50 border border-gray-200 rounded-2xl outline-none focus:ring-2 focus:ring-blue-600"
                                placeholder="Palavra-passe (admin123)"
                                value={password}
                                onChange={e => setPassword(e.target.value)}
                            />
                        </div>
                        <button 
                            type="submit"
                            disabled={loading}
                            className="w-full py-4 bg-blue-800 text-white rounded-2xl font-bold hover:bg-blue-700 transition-all disabled:bg-gray-300"
                        >
                            {loading ? "A carregar..." : "Entrar no Sistema"}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

// --- APP PRINCIPAL ---
export default function App() {
    return (
        <AppProvider>
            <RootNavigation />
        </AppProvider>
    );
}

const RootNavigation = () => {
    const { user } = useApp();
    return user ? <Dashboard /> : <LoginScreen />;
}