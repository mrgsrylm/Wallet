import { Navigate, Outlet } from 'react-router-dom';
import PropTypes from 'prop-types';
import AuthService from './services/AuthService';

const ProtectedRoute = ({ role }) => {
  const userRoles = AuthService.getCurrentUser()?.role;
  const isAuthorized = !role || userRoles;

  return isAuthorized ? <Outlet /> : <Navigate to="/unauthorized" replace />;
};

ProtectedRoute.propTypes = {
  roles: PropTypes.array,
}

export default ProtectedRoute;
