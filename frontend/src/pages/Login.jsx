import React, { useState } from 'react';

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    
    // Simulação de atraso de rede para feedback visual de carregamento
    setTimeout(() => {
      onLogin(username, password);
      setIsSubmitting(false);
    }, 1000);
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-6">
      {/* Card Principal de Login */}
      <div className="w-full max-w-md bg-white rounded-3xl shadow-xl overflow-hidden border border-gray-200">
        <div className="p-10">
          <div className="mb-10 text-center">
            {/* Logo Representativo */}
            <div className="w-16 h-16 bg-blue-800 rounded-2xl mx-auto mb-4 flex items-center justify-center shadow-lg">
              <span className="text-white text-3xl font-black">S</span>
            </div>
            <h1 className="text-2xl font-bold text-gray-900">SEPLAG Sênior</h1>
            <p className="text-sm text-gray-500 font-medium">Gestão de Artistas e Álbuns</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Campo de Utilizador */}
            <div className="space-y-1">
              <label className="text-xs font-bold text-gray-400 uppercase tracking-widest ml-1">Utilizador</label>
              <input 
                type="text" 
                required
                className="w-full p-4 bg-gray-50 border border-gray-200 rounded-2xl outline-none focus:ring-2 focus:ring-blue-600 focus:bg-white transition-all text-gray-800"
                placeholder="Insira o seu nome de utilizador"
                value={username}
                onChange={e => setUsername(e.target.value)}
              />
            </div>

            {/* Campo de Palavra-passe */}
            <div className="space-y-1">
              <label className="text-xs font-bold text-gray-400 uppercase tracking-widest ml-1">Palavra-passe</label>
              <input 
                type="password" 
                required
                className="w-full p-4 bg-gray-50 border border-gray-200 rounded-2xl outline-none focus:ring-2 focus:ring-blue-600 focus:bg-white transition-all text-gray-800"
                placeholder="••••••••"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
            </div>

            {/* Botão de Submissão com Estado de Carregamento */}
            <button 
              type="submit"
              disabled={isSubmitting}
              className="w-full py-4 bg-blue-800 text-white rounded-2xl font-bold text-lg hover:bg-blue-700 transition-all active:scale-[0.98] disabled:bg-gray-300 shadow-lg shadow-blue-100 flex justify-center items-center"
            >
              {isSubmitting ? (
                <div className="loader ease-linear rounded-full border-4 border-t-4 border-gray-200 h-6 w-6"></div>
              ) : (
                'Entrar no Sistema'
              )}
            </button>
          </form>
        </div>
        
        <div className="bg-gray-50 py-4 text-center">
          <p className="text-[10px] text-gray-400 font-bold uppercase tracking-tighter">
            Validação de Competência Técnica - Sênior
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;