import React from 'react'
import { useParams } from 'react-router-dom'

const CustomerProductDetail = () => {
    const params = useParams()
  return (
    <div>CustomerProductDetail: {params.id}</div>
  )
}

export default CustomerProductDetail