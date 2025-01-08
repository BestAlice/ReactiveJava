import './App.css';
import './variables.css'
import './buttons.css'
import './auto-layout.css'
import Header from './components/Header/Header';
import Main from './components/Main/Main'
import Footer from './components/Footer/Footer';

function App() {
  return (
    <>
      <Header />
      <div className="Plashka"></div> {/* Часть, разделяющая Хэдер и Основную часть  */}
      <Main />
      <Footer />
    </>
  );
}

export default App;
