import { useHistory } from 'react-router-dom';
import logo from './styles/Crop.png';

function Homepage() {
    const history = useHistory();

    function handleUserLoginClick() {
        history.push('/userLogin');
    }

    function handleUserSignUpClick() {
        history.push('/userSignUp');
    }



    return (
      <div className="min-h-screen flex flex-col justify-center items-center bg-gray-100">
          <div className="text-center">
              <h1 className="text-4xl font-bold mb-6 flex items-center justify-center">
                  Welcome to CropSense
                  <img src={logo} alt="Logo" className="h-16 w-16 ml-4" />
              </h1>
              <div className="space-x-4">
                  <button
                    onClick={handleUserLoginClick}
                    className="bg-blue-500 text-white py-2 px-4 rounded-lg shadow hover:bg-blue-700 transition duration-200"
                  >
                      Log In
                  </button>
                  <button
                    onClick={handleUserSignUpClick}
                    className="bg-blue-500 text-white py-2 px-4 rounded-lg shadow hover:bg-blue-700 transition duration-200"
                  >
                      Sign Up
                  </button>

              </div>
          </div>
      </div>
    );
}

export default Homepage;